import { lazy } from "react";
import {Routes, Route} from "react-router";

const Home = lazy(() => import('./pages/Home.tsx'))

export default function App() {
    return(
        <Routes>
            <Route path='/' element={<Home/>} />
        </Routes>
    );
}