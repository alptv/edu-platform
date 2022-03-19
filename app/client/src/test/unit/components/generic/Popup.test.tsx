import "@testing-library/jest-dom"
import {createPopupFactory, fromBoolean, Popup, PopupType} from "../../../../main/components/generic/Popup";
import {renderElement} from "../../Util";
import {screen, fireEvent} from "@testing-library/react"
import {ReactElement} from "react"


describe("Popup test", () => {

    describe("fromBoolean test", () => {

        test("should return SUCCESS if boolean is true", () => {
            const popupType = fromBoolean(true)
            expect(popupType).toBe(PopupType.SUCCESS)
        })

        test("should return FAILURE if boolean is false", () => {
            const popupType = fromBoolean(false)
            expect(popupType).toBe(PopupType.FAILURE)
        })
    })

    describe("createPopupFactory test", () => {
        let additionalActions: jest.Mock
        let setPopupType: jest.Mock
        let popupFactory: (_: PopupType) => ReactElement
        beforeEach(() => {
            additionalActions = jest.fn()
            setPopupType = jest.fn()
            popupFactory = createPopupFactory(
                "success", "failure",
                setPopupType, additionalActions
            )
        })

        test("should return empty component if popupType is NONE", () => {
            const popup = popupFactory(PopupType.NONE)

            expect(popup).toEqual(<></>)
        })

        test("should return popup with success message if popupType is SUCCESS", () => {
            const popup = popupFactory(PopupType.SUCCESS)

            expect(popup).toEqual(<Popup message={"success"}
                                         additionalActions={additionalActions}
                                         setPopupType={setPopupType}/>)
        })

        test("should return popup with failure message if popupType is FAILURE", () => {
            const popup = popupFactory(PopupType.FAILURE)

            expect(popup).toEqual(<Popup message={"failure"}
                                         additionalActions={additionalActions}
                                         setPopupType={setPopupType}/>)
        })
    })

    let additionalActions: jest.Mock
    let setPopupType: jest.Mock
    let popup: HTMLElement
    beforeEach(() => {
        additionalActions = jest.fn()
        setPopupType = jest.fn()
        popup = renderElement(<Popup message={"message"} additionalActions={additionalActions}
                                         setPopupType={setPopupType}/>)
    })

    test("should have message", () => {
        expect(screen.getByText("message")).toBeInTheDocument()
    })

    test("should have delete button", () => {
        const deleteButton = popup.querySelector<HTMLElement>(".DeleteButton")

        expect(deleteButton).toBeInTheDocument()
    })

     test("should set popupType NONE after click on close button", () => {
        const deleteButton = popup.querySelector<HTMLElement>(".DeleteButton")!!
        fireEvent.click(deleteButton)

        expect(setPopupType).toHaveBeenCalledWith(PopupType.NONE)
    })

    test("should use additional actions after click on close button", () => {
        const deleteButton = popup.querySelector<HTMLElement>(".DeleteButton")!!
        fireEvent.click(deleteButton)

        expect(additionalActions).toHaveBeenCalled()
    })
})