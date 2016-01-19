package org.gagauz.shop.database.model;

import org.gagauz.shop.database.model.enums.SmsStatus;

import javax.persistence.*;

@Entity
@Table(name = "SMS")
public class Sms extends Message {

    private static final long serialVersionUID = 4907966726555540912L;
    private Long phone;
    private SmsStatus status = SmsStatus.OPEN;

    @Column(nullable = false)
    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public SmsStatus getStatus() {
        return status;
    }

    public void setStatus(SmsStatus status) {
        this.status = status;
    }

    @Transient
    public String getPhoneString() {
        return null != phone ? phone.toString() : null;
    }

    @Transient
    public void setPhoneString(String phone) {
        phone = phone.replaceAll("[^0-9]", "");

        if (phone.charAt(0) == '8') {
            phone = "7" + phone.substring(1);
        }
        this.phone = Long.parseLong(phone);
    }

    @Override
    @Column(nullable = false, length = 500)
    public String getBody() {
        return super.getBody();
    }

    @Override
    public String toString() {
        return "Sms <id=" + id + ", phone=" + phone + ", subject=" + getSubject() + ", type=" + getType() + ">";
    }
}
