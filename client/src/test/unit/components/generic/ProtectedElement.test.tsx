import {ProtectedElement} from "../../../../main/components/generic/ProtectedElement"
import {renderNavigationElement} from "../../Util";
import {Route} from "react-router";


describe("ProtectedElement test", () => {

    test("protected element should return children if protection not needed", () => {
        const [, isStartUrl] = renderNavigationElement(
            <>
                <Route path={"/"} element={<></>}/>
                <Route path={"/protected"} element={
                    <ProtectedElement redirectTo={"/"} open={true} children={<></>}/>}
                />
            </>, "/protected"
        )
        expect(isStartUrl()).toBeTruthy()
    })

    test("protected element should redirect if protection needed", () => {
        const [, isStartUrl] = renderNavigationElement(
            <>
                <Route path={"/"} element={<></>}/>
                <Route path={"/protected"} element={
                    <ProtectedElement redirectTo={"/"} open={false} children={<></>}/>}
                />
            </>, "/protected"
        )
        expect(isStartUrl()).toBeFalsy()
    })
})