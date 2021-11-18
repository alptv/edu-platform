import {Async} from "react-async"
import React, {ReactNode} from "react";

export const LoadingComponent = <Data extends any>(props: {
    loader: () => Promise<Data>
    children: (data: Data) => ReactNode
}) => (
    <Async promiseFn={props.loader}>
        <Async.Pending>Loading...</Async.Pending>
        <Async.Fulfilled>
            {(data: Data) => props.children(data)}
        </Async.Fulfilled>
        <Async.Rejected>Something went wrong</Async.Rejected>
    </Async>
)