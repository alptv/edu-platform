import {RestClient} from "../util/RestClient";
import {ServiceImpl, Service} from "./Service";

function getEnvVariableSafe(envVariableName : string) : string {
    const envVariable = process.env[envVariableName]
    if (envVariable) {
        return envVariable
    } else {
        throw new Error(`Missing environment variable: ${envVariableName}`)
    }
}

const API_URL: string = getEnvVariableSafe("REACT_APP_SERVER_URL")
const serviceRestClient: RestClient = new RestClient(API_URL)
export const service: Service = new ServiceImpl(serviceRestClient)
