import axios from "axios";
import authHeader from "./authHeader";

const API_URL = "http://localhost:8080/api/payment/";

class PaymentService {
  beginPaymentProcess(magazine, paymentType) {
    return axios
      .post(
        API_URL + "beginPaymentProcess",
        {
          magazine,
          paymentType,
        },
        { headers: authHeader() }
      )
      .then((response) => {
        return response.data;
      });
  }
}

export default new PaymentService();
