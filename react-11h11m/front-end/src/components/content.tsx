import Sidebar from "./sidebar";
import ContentHeader from "./contentHeader";
import ContentFooter from "./contentFooter";
import ContentBody from "./contentBody";

const Content = () => {
    return (
        <div className="content">
            <Sidebar/>
            <div className="inner-content">
                <ContentHeader/>
                <ContentBody/>
                <ContentFooter/>
            </div>
        </div>
    )
}

export default Content;