import {List} from "../../../../main/components/generic/List"
import {renderElement} from "../../Util";

const Element = (index: number) => <div className="Element" id={`index${index}`}/>

describe("List test", () => {

    test("list with type 'o' should be ordered", () => {
        const list = renderElement(<List type="o" items={[]} element={Element}/>)
        expect(list.tagName).toBe("OL")
    })

    test("list with type 'u' should be unordered", () => {
        const list = renderElement(<List type="u" items={[]} element={Element}/>)
        expect(list.tagName).toBe("UL")
    })

    test("list should create all items", () => {
        const list = renderElement(<List type="o" items={[0, 1, 2, 3, 4]} element={Element}/>)
        const listElements = list.querySelectorAll(".Element")
        expect(listElements.length).toBe(5)
        listElements.forEach((element, index) => {
            expect(element.id).toBe(`index${index}`)
        })
    })
})