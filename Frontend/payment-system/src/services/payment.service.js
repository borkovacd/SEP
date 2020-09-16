import axios from "axios";
import authHeader from "./authHeader";

const API_URL = "http://localhost:8080/api/payment/";

class PaymentService {
  async beginPaymentProcess(magazine, paymentType) {
    return await axios.post(
      API_URL + "beginPaymentProcess",
      {
        magazine,
        paymentType,
      },
      { headers: authHeader() }
    );
  }
}

export default new PaymentService();
