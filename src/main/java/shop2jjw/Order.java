package shop2jjw;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String productId;
    private Integer qty;

    @PostPersist
    public void onPostPersist(){
        Ordered ordered = new Ordered();
        BeanUtils.copyProperties(this, ordered);
        ordered.publishAfterCommit();


        //Ordered ordered = new Ordered();
        //BeanUtils.copyProperties(this, ordered);
        //ordered.publishAfterCommit();


    }

    @PreRemove
    public void onPreRemove(){
        OrderCanceled orderCanceled = new OrderCanceled();
        BeanUtils.copyProperties(this, orderCanceled);
        orderCanceled.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        //shop2jjw.external.Cancellation cancellation = new shop2jjw.external.Cancellation();
        // mappings goes here
        //Application.applicationContext.getBean(shop2jjw.external.CancellationService.class)
        //    .cancel(cancellation);


        //OrderCanceled orderCanceled = new OrderCanceled();
        //BeanUtils.copyProperties(this, orderCanceled);
       // orderCanceled.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        shop2jjw.external.Cancellation cancellation = new shop2jjw.external.Cancellation();
        // mappings goes here
        Application.applicationContext.getBean(shop2jjw.external.CancellationService.class)
            .cancel(cancellation);


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }




}
