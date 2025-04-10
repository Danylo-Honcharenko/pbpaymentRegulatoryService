package ua.privat.regulatoryservice.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.privat.clientlib.utils.PaymentApiUtilI;
import ua.privat.utils.dto.RegularPaymentDTO;

import java.util.List;

@Service
public class RegularPaymentService {

    private final PaymentApiUtilI apiUtil;

    public RegularPaymentService(@Qualifier("business") PaymentApiUtilI apiUtil) {
        Assert.notNull(apiUtil,
                "apiUtil must not be null");
        this.apiUtil = apiUtil;
    }

    public List<RegularPaymentDTO> checkPaymentsForTheNeedToWriteOff() {
        return apiUtil.doGetList("/write-off-payment", RegularPaymentDTO.class, (flux) -> flux);
    }
}
