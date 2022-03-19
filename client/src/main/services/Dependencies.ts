import {RestClient} from "../util/RestClient";
import {ServiceImpl, Service} from "./Service";

const API_URL: string = "http://localhost:8080"
const serviceRestClient: RestClient = new RestClient(API_URL)
export const service: Service = new ServiceImpl(serviceRestClient)
