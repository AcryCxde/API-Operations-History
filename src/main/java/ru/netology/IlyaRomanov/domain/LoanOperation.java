package ru.netology.IlyaRomanov.domain;

public class LoanOperation extends Operation {

    private int loanId;

    public LoanOperation(int id, int sum, String currency, String merchant, int loanId, int clientId) {
        super(id, sum, currency, merchant, clientId);
        this.loanId = loanId;
    }


    @Override
    public String toString()
    {
        return "{id=" + getId() + ", sum=" + getSum() + ", currency=" + getCurrency() + ", merchant=" + getMerchant() + ", loan id=" + loanId + "}";
    }
}
