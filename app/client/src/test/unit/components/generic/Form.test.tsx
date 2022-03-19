import "@testing-library/jest-dom"
import {renderElement} from "../../Util";
import {screen, fireEvent} from "@testing-library/react"
import {Form, InputField} from "../../../../main/components/generic/Form";

describe("InputField test", () => {
    let field: HTMLElement
    let setValue: jest.Mock
    beforeEach(() => {
        setValue = jest.fn()
        field = renderElement(<InputField label="label" type="text"
                                          value="value" setValue={setValue}/>)
    })

    test("should have label", () => {
        expect(screen.getByText("label")).toBeInTheDocument()
    })

    test("should use setValue on change", () => {
        const input = field.querySelector<HTMLElement>(".Input")
        expect(input).toBeInTheDocument()

        fireEvent.change(input!!, {target: {value: "newValue"}})
        expect(setValue).toHaveBeenCalledWith("newValue")
    })

    test("should use value as default field property", () => {
        const input = field.querySelector<HTMLElement>(".Input")!!
        expect(input.getAttribute("value")).toBe("value")
    })
})

describe("Form test", () => {

    let onSubmit: jest.Mock
    beforeEach(() => {
        onSubmit = jest.fn()
        renderElement(<Form header="header" onSubmit={onSubmit} buttonText="buttonText"/>)
    })

    test("should have header", () => {
        expect(screen.getByText("header")).toBeInTheDocument()
    })

    test("should have submit button", () => {
        expect(screen.getByText("buttonText")).toBeInTheDocument()
    })

    test("should use onSubmit function on submit click", () => {
        fireEvent.submit(screen.getByText("buttonText"))
        expect(onSubmit).toHaveBeenCalled()
    })
})