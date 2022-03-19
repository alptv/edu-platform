import {useState, useEffect} from "react"
import {deserialize, serialize} from "class-transformer"


export declare type Setter<T> = (value: T) => void;

export declare type ClassConstructor<T> = {
    new(...args: any[]): T;
};

export const NO_DEPENDENCY = [null]

export function useLoader<T>(setValue: Setter<T>, loader: () => Promise<T>, dependency?: any[]) {
    useEffect(() => {
        loader().then(value => setValue(value))
    }, dependency)
}


export function useArrayState<T>(defaultValue?: T, size?: number): [Array<T>, (index: number) => T, (index: number, value: T) => void] {
    const defaultArrayValue = defaultValue ? Array<T>(size ?? 0).fill(defaultValue) : []
    const [array, setArray] = useState<Array<T>>(defaultArrayValue)
    const set = (index: number, value: T) => setArray(prevArray =>
        [...prevArray.slice(0, index), value, ...prevArray.slice(index + 1, prevArray.length)])
    const get = (index: number) => array[index]
    return [array, get, set]
}


export function saveToLocalStorage<T>(key: string, value: T) {
    localStorage.setItem(key, serialize(value))
}

export function getFromLocalStorage<T>(key: string, classConstructor: ClassConstructor<T>) {
    const value = localStorage.getItem(key)
    if (value) {
        return deserialize(classConstructor, value)
    } else {
        return null
    }
}


export function removeFromLocalStorage(key: string) {
    localStorage.removeItem(key)
}


