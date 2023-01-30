const Header = () => {
    return (
        <header className="header">
            <h1 className="logo">
                <a></a>
            </h1>
            <div className="user-info">
                <div className="name">
        <span className="fz-14 fc-1 fw-medium">
            관리자명
        </span>
                </div>
                <a className="name">
        <span className="fz-14 fc-4">
            관리자 로그아웃
        </span>
                </a>
                <a className="notify">
                    <i className="ico ico-notify"></i>
                    <span className="badge">99+</span>
                </a>
                <a className="biz-money">
                    <span className="auto-charge off">자동충전 <span className="eng">OFF</span></span>
                    <span className="auto-charge on">자동충전 <span className="eng">ON</span></span>
                    <span className="fz-14 fc-1 fw-medium">비즈머니</span>
                    <span className="fz-14 fc-2">₩9,999,999</span>
                </a>
                <div className="comp-dropdown shadow">
                    <a className="dropdown-toggle" data-toggle="dropdown">
                        <i className="ico ico-shadow"></i>
                        <span className="fz-14 fc-2 fw-medium">메이커 로그인</span>
                    </a>
                    <div className="dropdown-menu">
                        <div className="box-body">
                            <div className="input-group comp-search">
                                <div className="inner-input-group selected">
                                    <input type="text" className="tf-comm" placeholder="힌트 텍스트"/>
                                        <i className="ico ico-search"></i>
                                        <i className="ico ico-delete"></i>
                                </div>
                                <ul className="opt-selectbox">
                                    <li className="opt-menu selected">
                                        <p className="fc-1"><span className="txt-mark">옵션</span> 01</p>
                                    </li>
                                    <li className="opt-menu">
                                        <p className="fc-1"><span className="txt-mark">옵션</span> 02</p>
                                    </li>
                                    <li className="opt-menu">
                                        <p className="fc-1"><span className="txt-mark">옵션</span> 03</p>
                                    </li>
                                    <li className="opt-menu">
                                        <p className="fc-1"><span className="txt-mark">옵션</span> 04</p>
                                    </li>
                                    <li className="comp-no-data">
                                        <div className="box-group">
                                            <i className="ico ico-no-search"></i>
                                            <p className="fz-14 fc-2">검색 결과가 없습니다.</p>
                                            <p className="fz-12 fc-3">검색어를 다시 입력하여 검색해주세요.</p>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div className="box-footer">
                            <button type="button" className="btn btn-tertiary-mint expand">메이커 로그아웃</button>
                        </div>
                    </div>
                </div>
            </div>
        </header>
    )
}

export default Header;