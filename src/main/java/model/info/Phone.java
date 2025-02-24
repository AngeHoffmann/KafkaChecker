package model.info;


import java.util.List;

public class Phone {
    private String type;
    private String number;
    private List<AdditionalParameter> additionalParameters;

    public Phone(String type, String number, List<AdditionalParameter> additionalParameters) {
        this.type = type;
        this.number = number;
        this.additionalParameters = additionalParameters;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<AdditionalParameter> getAdditionalParameters() {
        return additionalParameters;
    }

    public void setAdditionalParameters(List<AdditionalParameter> additionalParameters) {
        this.additionalParameters = additionalParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;

        // Сравниваем type и number
        if (!type.equals(phone.type)) return false;
        if (!number.equals(phone.number)) return false;

        // Сравниваем additionalParameters с учетом null
        if (additionalParameters == null && phone.additionalParameters != null) return false;
        if (additionalParameters != null && !additionalParameters.equals(phone.additionalParameters)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + number.hashCode();
        result = 31 * result + (additionalParameters != null ? additionalParameters.hashCode() : 0);
        return result;
    }
}