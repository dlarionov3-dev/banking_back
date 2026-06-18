package larionov.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import larionov.enums.CurrencyCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "accounts")
public class AccountEntity extends BaseEntity {

    @NotBlank
    @Size(max = 34)
    @Column(
            name = "account_number",
            nullable = false,
            unique = true,
            length = 34
    )
    private String accountNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 3)
    private CurrencyCode currency;

    @NotNull
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private UserEntity user;

    @OneToMany(mappedBy = "senderAccount")
    private List<TransactionEntity> outgoingTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "receiverAccount")
    private List<TransactionEntity> incomingTransactions = new ArrayList<>();

}
