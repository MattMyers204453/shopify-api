package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrderService orderService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Order>> getAll(){
        List<Order> allOrders = orderService.getAll();
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    @GetMapping(path="/fetch", produces = "application/json")
    public ResponseEntity<> fetchAll(){
        List<Order> allOrdersFromShopify = orderService.fetchAll();
        orderService.persistOrders(allOrdersFromShopify);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(produces = "application/json")
    public ResponseEntity<> deleteAll(@PathVariable("id") Long id){
        orderService.deleteAllOrders();
        return new ResponseEntity<>(HttpStatus.OK);
}
