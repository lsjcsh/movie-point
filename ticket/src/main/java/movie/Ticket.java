package movie;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Ticket_table")
public class Ticket {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long bookingId;
    private String seat;
    private String movieName;
    private Integer qty;
    private String status;

    @PostPersist
    public void onPostPersist(){

        Created created = new Created();
        BeanUtils.copyProperties(this, created);
        created.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate(){

        if("Printed".equals(status)){
            Printed printed = new Printed();
            BeanUtils.copyProperties(this, printed);
            printed.setStatus("Printed");

            System.out.println("*********************");
            System.out.println("포인트 동기식 생성");
            System.out.println("*********************");
            movie.external.Point point = new movie.external.Point();
            // mappings goes here
            point.setBookingId(printed.getBookingId());
            point.setScore(100);      
            point.setContents("create point"); 
            point.setStatus("Pointed");
            TicketApplication.applicationContext.getBean(movie.external.PointService.class)
                .point(point);

            printed.publishAfterCommit();
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
