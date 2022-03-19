import "./Form.css"
import {ReactElement} from "react"


export const Form = (props: {
    header: string,
    onSubmit: () => void,
    children?: ReadonlyArray<ReactElement>,
    buttonText: string
}) => (
    <form className="Form" onSubmit={(event) => {
        event.preventDefault()
        props.onSubmit()
    }}>
        <h1>{props.header}</h1>
        <div className="Fields">
            {props.children}
        </div>
        <br/>
        <input type="submit" className="Submit" value={props.buttonText}/>
    </form>
)


export const InputField = (props: {
    label: string
    type: string
    value?: string
    setValue?: (_: string) => void
}) => {
    const setValue = (value: string) => {
        if (props.setValue) props.setValue(value)
    }
    return (
        <div className="InputField">
            <label className="Label">{props.label}</label>
            <input onChange={e => setValue(e.target.value)}
                   value={props.value}
                   className="Input"
                   type={props.type}
            />
        </div>
    )
}
