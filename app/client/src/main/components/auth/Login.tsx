import {useState} from "react"
import {useNavigate} from "react-router"
import {Form, InputField} from "../generic/Form";
import {User} from "../../services/CourseService";


export const Login = (props: {
    setUser: (_: User) => void,
}) => {
    const [login, setLogin] = useState("")
    const [password, setPassword] = useState("")
    const navigate = useNavigate()
    const onSubmit = () => {
        props.setUser(new User("Eжжи Девито"))
        navigate("/")
    }
    return (
        <div className="Login">
            <Form header="Вход" buttonText="Войти" onSubmit={onSubmit}>
                <InputField value={login} setValue={setLogin} label="Логин" type="text"/>
                <InputField value={password} setValue={setPassword} label="Пароль" type="password"/>
            </Form>
        </div>
    )
}

