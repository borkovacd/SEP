import http from "../http-common";
import axios from "axios";
import authHeader from "./authHeader";

const API_URL = "https://localhost:8080/api/test/";

class UserService {
  getPublicContent() {
    return axios.get(API_URL + "all");
  }

  /*
  You can see that we add a HTTP header with the help of authHeader() function when requesting authorized resource.
  */
  getUserBoard() {
    return axios.get(API_URL + "user", { headers: authHeader() });
  }

  getAdminBoard() {
    return axios.get(API_URL + "admin", { headers: authHeader() });
  }

  getAll() {
    return http.get("/users", { headers: authHeader() });
  }

  get(id) {
    return http.get(`/users/${id}`, { headers: authHeader() });
  }

  create(data) {
    return http.post("/users", data, { headers: authHeader() });
  }

  update(id, data) {
    return http.put(`/users/${id}`, data, { headers: authHeader() });
  }

  block(id, data) {
    var options = { params: {}, headers: authHeader() };
    options["params"]["blocked"] = true;
    return http.put(`/users/${id}`, data, options);
  }

  delete(id) {
    return http.delete(`/users/${id}`, { headers: authHeader() });
  }

  deleteAll() {
    return http.delete(`/tutorials`);
  }

  findByTitle(title) {
    return http.get(`/tutorials?title=${title}`);
  }
}

export default new UserService();
