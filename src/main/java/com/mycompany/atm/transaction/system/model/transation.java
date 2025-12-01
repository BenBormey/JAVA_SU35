package com.mycompany.atm.transaction.system.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class transation {

    private long id;
    private long accountId;
    private int userId;
    private String tranType;
    private BigDecimal amount;
    private boolean isKh;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private OffsetDateTime createdAt;
    private String note;

    public transation() {
    }

    public transation(long id,
                       long accountId,
                       int userId,
                       String tranType,
                       BigDecimal amount,
                       boolean isKh,
                       BigDecimal balanceBefore,
                       BigDecimal balanceAfter,
                       OffsetDateTime createdAt,
                       String note) {
        this.id = id;
        this.accountId = accountId;
        this.userId = userId;
        this.tranType = tranType;
        this.amount = amount;
        this.isKh = isKh;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        this.createdAt = createdAt;
        this.note = note;
    }

    // 🔹 Getters & Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isKh() {
        return isKh;
    }

    public void setKh(boolean kh) {
        isKh = kh;
    }

    public BigDecimal getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(BigDecimal balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", userId=" + userId +
                ", tranType='" + tranType + '\'' +
                ", amount=" + amount +
                ", isKh=" + isKh +
                ", balanceBefore=" + balanceBefore +
                ", balanceAfter=" + balanceAfter +
                ", createdAt=" + createdAt +
                ", note='" + note + '\'' +
                '}';
    }
}
