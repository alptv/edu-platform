import "./App.css"
import React, {ReactElement, useState} from "react"
import {BrowserRouter, Routes, Route} from "react-router-dom"
import {NavigationBar} from "./components/NavigationBar"
import {CoursesCatalog} from "./components/course/CoursesCatalog"
import {CoursePage} from "./components/course/CoursePage"
import {LessonContent} from "./components/course/content/LessonContent"
import {ProtectedElement} from "./components/generic/ProtectedElement";
import {Login} from "./components/auth/Login";
import {Registration} from "./components/auth/Registration";
import {Profile} from "./components/profile/Profile";
import {Main} from "./components/Main";
import 'reflect-metadata'
import {service} from "./services/Dependencies";
import {Greeting} from "./components/course/content/Greeting";

export function App() {

    const [user, setUser] = useState(service.getUser())
    const isLoggedIn = user !== null
    const logOut = () => {
        service.logout()
        setUser(null)
    }

    const ProtectedElementByLogin = (props: {
        children: ReactElement
    }) => (
        <ProtectedElement redirectTo={"/login"} open={isLoggedIn}>
            {props.children}
        </ProtectedElement>
    )

    return (
        <BrowserRouter>
            <div className="App">
                <NavigationBar logOut={logOut} isLoggedIn={isLoggedIn}/>
                <Routes>
                    <Route path="/" element={
                        <ProtectedElementByLogin>
                            <Main/>
                        </ProtectedElementByLogin>
                    }/>

                    <Route path="/courses" element={
                        <ProtectedElementByLogin>
                            <CoursesCatalog/>
                        </ProtectedElementByLogin>
                    }/>

                    <Route path="/courses/:courseId" element={
                        <ProtectedElementByLogin>
                            <CoursePage/>
                        </ProtectedElementByLogin>}>
                        <Route path={"lesson/:lessonId"} element={
                            <ProtectedElementByLogin>
                                <LessonContent/>
                            </ProtectedElementByLogin>
                        }/>
                        <Route path={""} element={
                            <ProtectedElementByLogin>
                                <Greeting/>
                            </ProtectedElementByLogin>
                        }/>
                    </Route>

                    <Route path={"/profile"} element={
                        <ProtectedElementByLogin>
                            {user ? <Profile user={user}/> : <></>}
                        </ProtectedElementByLogin>
                    }/>
                    <Route path="/login" element={
                        <ProtectedElement redirectTo={"/"} open={!isLoggedIn}>
                            <Login setUser={setUser}/>
                        </ProtectedElement>
                    }/>

                    <Route path="/register" element={
                        <ProtectedElement redirectTo={"/"} open={!isLoggedIn}>
                            <Registration setUser={setUser}/>
                        </ProtectedElement>
                    }/>

                    <Route path="/*" element={
                        <h1>Not found</h1>
                    }/>
                </Routes>
            </div>
        </BrowserRouter>
    )
}
