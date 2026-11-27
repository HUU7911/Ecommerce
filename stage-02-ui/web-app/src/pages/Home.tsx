import {lazy} from "react";

const Header = lazy(() => import('../components/Header'))

export default function Home() {
    return(
        <>
            <Header/>
            <h1>Hello everyone</h1>
        </>
    );
}