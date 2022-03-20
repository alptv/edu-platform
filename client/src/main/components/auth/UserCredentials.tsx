import {useState} from "react"
import {useNavigate} from "react-router"
import {Form, InputField} from "../generic/Form";
import {fromBoolean, Popup, PopupType} from "../generic/Popup";
import {User} from "../../services/Data";
import {Setter} from "../../util/Util";


export const UserCredentials = (props: {
    headerText: string,
    submitText: string,
    failureText: string,
    setUser: Setter<User>,
    submit: (login: string, password: string) => Promise<User | null>
}) => {
    const [login, setLogin] = useState("")
    const [password, setPassword] = useState("")
    const [popupType, setPopupType] = useState(PopupType.NONE)

    const navigate = useNavigate()

    const onSubmit = () => {
        props.submit(login, password).then(user => {
            const success = user != null
            setPopupType(fromBoolean(success))
            if (success) {
                props.setUser(user)
                navigate("/")
            }
        })
    }
    return (
        <>
            <Form header={props.headerText} buttonText={props.submitText} onSubmit={onSubmit}>
                <InputField value={login} setValue={setLogin} label="Логин" type="text"/>
                <InputField value={password} setValue={setPassword} label="Пароль" type="password"/>
            </Form>
            {popupType === PopupType.FAILURE &&
            <Popup additionalActions={() => {
                setPassword("")
                setLogin("")
            }} setPopupType={setPopupType} message={props.failureText}/>}
        </>
    )
}