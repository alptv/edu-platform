import {Form, InputField} from "../generic/Form"
import {useState} from "react"
import {useNavigate} from "react-router"
import {User} from "../../services/CourseService";


export const Registration = (props : {
    setUser : (_ : User) => void
}) => {
    const [login, setLogin] = useState("")
    const [password, setPassword] = useState("")
    const navigate = useNavigate()

    const onSubmit = () => {
        props.setUser(new User("Eжжи Cарма"))
        navigate("/")
    }
    return (
        <div className="Registration">
            <Form header="Регистрация" buttonText="Зарегистрироваться" onSubmit={onSubmit}>
                <InputField value={login} setValue={setLogin} label="Логин" type="text"/>
                <InputField value={password} setValue={setPassword} label="Пароль" type="password"/>
            </Form>
        </div>
    )
}