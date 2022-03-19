import {UserCredentials} from "./UserCredentials"
import {service} from "../../services/Dependencies"
import {User} from "../../services/Data"
import {Setter} from "../../util/Util"


export const Login = (props: {
    setUser: Setter<User>
}) => {
    const submit = (login: string, password: string) => service.enter(login, password)
    return (
        <div className="Login">
            <UserCredentials
                setUser={props.setUser}
                submit={submit}
                headerText="Вход"
                submitText="Войти"
                failureText="Неверный логин или пароль"/>
        </div>)
}