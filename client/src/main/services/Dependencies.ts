import {RestClient} from "../util/RestClient";
import {ServiceImpl, Service} from "./Service";

function getEnvVariableOrDefault(envVariableName: string, defaultVariableName: string): string {
    const envVariable = process.env[envVariableName]
    return envVariable ?? defaultVariableName
}

const API_URL: string = getEnvVariableOrDefault("REACT_APP_SERVER_URL", "http://localhost:7001")
const serviceRestClient: RestClient = new RestClient(API_URL)
export const service: Service = new ServiceImpl(serviceRestClient)
