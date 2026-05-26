package Backend.entites;

import Backend.dtos.RiceDto;
import jakarta.persistence.*;

@Entity
public class Rice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "Id" , nullable = false)
    private Long id;

    @Column(name = "name" , nullable = false)
    private String name;

    @Column(name = "price" , nullable = false)
    private double price;

    @Column(name = "description" , nullable = false)
    private String description;

    @Column(name = "image_Url" , nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Client owner;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }



    public RiceDto getDto(){

        RiceDto riceDto = new RiceDto();

        riceDto.setId(id);
        riceDto.setName(name);
        riceDto.setPrice(price);
        riceDto.setDescription(description);
        riceDto.setImageUrl(imageUrl);
        if(owner != null){
            riceDto.setOwnerName(owner.getFirstName());
        }
        return riceDto;
    }
}
