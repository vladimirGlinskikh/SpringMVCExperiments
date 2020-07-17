package kz.zhelezyaka.model;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "owner")
public class Car {
    private Integer id;
    private String model;
    private User owner;
}
