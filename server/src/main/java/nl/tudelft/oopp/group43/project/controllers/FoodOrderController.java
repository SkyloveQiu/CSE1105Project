package nl.tudelft.oopp.group43.project.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.FoodOrder;
import nl.tudelft.oopp.group43.project.models.FoodOrderDetails;
import nl.tudelft.oopp.group43.project.models.FoodOrderDetailsId;
import nl.tudelft.oopp.group43.project.models.FoodProduct;
import nl.tudelft.oopp.group43.project.models.Reservation;
import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.payload.ErrorResponse;
import nl.tudelft.oopp.group43.project.repositories.BuildingFoodProductRepository;
import nl.tudelft.oopp.group43.project.repositories.FoodOrderDetailsRepository;
import nl.tudelft.oopp.group43.project.repositories.FoodOrderRepository;
import nl.tudelft.oopp.group43.project.repositories.ReservationRepository;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodOrderController {

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private FoodOrderDetailsRepository foodOrderDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuildingFoodProductRepository buildingFoodProductRepository;


    @GetMapping("/foodOrder")
    @ResponseBody
    public List<FoodOrder> getFoodOrder() {
        return foodOrderRepository.findAll();
    }

    // {"building":0,"reservation":1,"user":"a@tudelft.nl","time":"17:00:00"}

    /**
     * Process a food order.
     *
     * @param newFoodOrder describes a new FoodOrder
     * @return returns a message
     */
    @PostMapping("/foodOrder")
    @ResponseBody
    public ResponseEntity createFoodOrder(@RequestBody FoodOrder newFoodOrder,
                                          @RequestParam(value = "token", defaultValue = "invalid") String token,
                                          @RequestParam(value = "order", defaultValue = "invalid") String order,
                                          @RequestParam(value = "away", defaultValue = "invalid") String takeAway) {


        if (token.equals("invalid") || order.equals("invalid")) {
            ErrorResponse errorResponse = new ErrorResponse("Food Reservation error", "Please also send the token and/or the order.", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        try {
            if (userRepository.findUserByToken(token) == null || !userRepository.findUserByToken(token).getUsername().equals(newFoodOrder.getUser().getUsername())) {
                ErrorResponse errorResponse = new ErrorResponse("Food Reservation error", "This user does not exist or the token is invalid.", HttpStatus.FORBIDDEN.value());
                return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Food Reservation error", "This user does not exist or the token is invalid.", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        if (!takeAway.equals("invalid")) {
            newFoodOrder.setReservation(new Reservation(1));
        }


        String processedOrder;
        processedOrder = order.replaceAll("-", " ");
        List<Integer> orderList = new ArrayList<>();
        List<Integer> finalOrder = new ArrayList<>();

        System.out.println(processedOrder);

        Scanner sc = new Scanner(processedOrder);

        while (sc.hasNext()) {
            orderList.add(sc.nextInt());
        }

        for (int i = 0; i < orderList.size(); i = i + 3) {
            if (buildingFoodProductRepository.existsBuildingFoodProductByBuildingAndAndFoodProduct(new Building(orderList.get(i).intValue()), new FoodProduct(orderList.get(i + 1)))) {
                finalOrder.add(orderList.get(i));
                finalOrder.add(orderList.get(i + 1));
                finalOrder.add(orderList.get(i + 2));
            }
        }

        int currId = foodOrderRepository.save(newFoodOrder).getId();

        List<FoodOrderDetails> detailsList = new ArrayList<>();

        //public FoodOrderDetails(FoodOrderDetailsId id, FoodProduct foodProduct, FoodOrder foodOrder, int amount) {
        for (int i = 0; i < finalOrder.size(); i = i + 3) {
            detailsList.add(new FoodOrderDetails(new FoodOrderDetailsId(currId, orderList.get(i + 1).intValue()), new FoodProduct(finalOrder.get(i + 1)), newFoodOrder, finalOrder.get(i + 2)));
        }


        for (FoodOrderDetails i : detailsList) {
            foodOrderDetailsRepository.save(i);
            System.out.println(i + "    ");
        }

        ErrorResponse okResponse = new ErrorResponse("Food Reservation OK", "You have successfully ordered your food: " + finalOrder.toString(), HttpStatus.OK.value());
        return new ResponseEntity<>(okResponse, HttpStatus.OK);
    }

    @GetMapping("/foodOrder/moreDetails")
    @ResponseBody
    public List<FoodOrder> getFoodOrderByUser(@RequestParam(value = "token", defaultValue = "invalid") String token) {

        if (token.equals("invalid")) {
            return new ArrayList<>();
        }

        User user = userRepository.findUserByToken(token);

        if (user == null) {
            return new ArrayList<>();
        }

        return foodOrderRepository.getAllByUser(user);


    }
}
