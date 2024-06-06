package course.courseWork.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userRoles")
@Entity(name = "userRoles")
@Setter
@Getter
public class UserRoles {
    @Id
    @GeneratedValue(generator = "userRole_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "userRole_id_seq", sequenceName = "userRole_id_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "userRoleId")
    private Long userRoleId;

    @Enumerated // помечаем т.к. enum
    private UserAuthority userAuthority;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users users;

}



