package com.bascker.manage.model;

/**
 * 客户模型
 *
 * @author bascker
 */
public class Customer {

    private long id;

    private String name;

    /**
     * 联系人
     */
    private String contact;

    private String telephone;

    private String email;

    /**
     * 备注
     */
    private String remark;

    public Customer() {}

    public Customer(final long id, final String name) {
        this.id = id;
        this.name = name;
    }

    private Customer(final Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.contact = builder.contact;
        this.telephone = builder.telephone;
        this.email = builder.email;
        this.remark = builder.remark;
    }

    public static class Builder {
        // 必选
        private long id;
        private String name;

        // 可选
        private String contact;
        private String telephone;
        private String email;
        private String remark;

        public Builder(final long id, final String name) {
            this.id = id;
            this.name = name;
        }

        public Builder setContact(final String contact) {
            this.contact = contact;
            return this;
        }

        public Builder setTelephone(final String telephone) {
            this.telephone = telephone;
            return this;
        }

        public Builder setEmail(final String email) {
            this.email = email;
            return this;
        }

        public Builder setRemark(final String remark) {
            this.remark = remark;
            return this;
        }

        public Customer builder() {
            return new Customer(this);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(final String contact) {
        this.contact = contact;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}