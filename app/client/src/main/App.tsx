import React from 'react';
import './App.css';
import NavigationBar from "./NavigationBar";
import {BrowserRouter, Routes, Route} from "react-router-dom";

function App() {
    return (
        <BrowserRouter>
            <div className="App">
                <NavigationBar/>
                <Routes>
                    <Route path="/" element={<h1>Home</h1>}/>
                    <Route path="/courses" element={<h1>Courses</h1>}/>
                    <Route path="/profile" element={<h1>Profile</h1>}/>
                </Routes>
            </div>
        </BrowserRouter>
    );
}

export default App;