package coffeeshop.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "QrCode")
public class QrCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String TableIndex;

    private String content;
    private String imagePath;

    public QrCodeEntity() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTableIndex() {
        return TableIndex;
    }

    public void setTableIndex(String tableIndex) {
        TableIndex = tableIndex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
