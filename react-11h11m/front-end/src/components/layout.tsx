import Header from "./header";
import ContentHeader from "./contentHeader";
import ContentFooter from "./contentFooter";
import Sidebar from "./sidebar";

const Layout = () => {
    return (
        <>
            <Header/>
            <Sidebar/>
            <ContentHeader/>
            <ContentFooter/>
        </>
    )
}

export default Layout;