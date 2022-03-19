import {deserialize, deserializeArray} from "class-transformer"
import {ClassConstructor} from "./Util";

export class RestClient {
    readonly apiRoot: string

    constructor(apiRoot: string) {
        this.apiRoot = apiRoot
    }

    apiUrl(resource: string) {
        return this.apiRoot + resource;
    }

    async send(method: string, resource: string, body?: string): Promise<Response> {
        return fetch(this.apiUrl(resource), {
            method: method,
            body: body,
            credentials: "include"
        })
    }


    async load<T>(resource: string, classConstructor: ClassConstructor<T>): Promise<T> {
        return this.send("GET", resource)
            .then(response => response.text())
            .then(json => deserialize(classConstructor, json))
    }

    async loadArray<T>(resource: string, classConstructor: ClassConstructor<T>): Promise<T[]> {
        return this.send("GET", resource)
            .then(response => response.text())
            .then(jsonArray => deserializeArray(classConstructor, jsonArray))
    }
}


