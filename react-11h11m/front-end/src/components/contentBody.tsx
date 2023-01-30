import DashboardBox from "./fragment/dashboardBox";

const ContentBody = () => {
    return (
        <div className="content-body">
            <div className="container-fluid">
                <div className="row">
                    <DashboardBox title={'충전사업자번호'} content={'메이커명칭'} icon={false} />
                    <DashboardBox title={'휴대폰 번호'} content={'010-1234-5678'} icon={true} />
                    {/*<DashboardBox title={'Wa-biz 약관 동의일'} content={'YYYY-MM-DD'} icon={true} />*/}
                    <div className="col col-4">
                        <div className="wrap-section wrap-tbl wrap-dashboard">
                            <div className="box-body">
                                <div className="tbl">
                                    <dl className="vertical">
                                        <dt>
                                            <div className="dt-inner">
                                                <span className="fz-14 fc-4">
                                                    Wa-biz 약관 동의일
                                                    <a className="ico-tooltip tooltip-f" title=""></a>
                                                </span>
                                            </div>
                                        </dt>
                                        <dd>
                                            <div className="form-group flex-container">
                                                <div className="box-left">
                                                    <span className="comp-txt">
                                                        <span className="table">
                                                            <span className="table-cell">
                                                                <b className="fz-20 fc-2 fw-medium">YYYY-MM-DD</b>
                                                            </span>
                                                        </span>
                                                    </span>
                                                </div>
                                                <div className="box-right">
                                                    <span className="comp-txt">
                                                        <span className="table">
                                                            <span className="table-cell">
                                                                <b className="fz-12 fc-6"><a
                                                                    className="txt-link">개인정보 처리방침 보기</a></b>
                                                                <b className="fz-12 fc-6"><a className="txt-link">광고서비스 이용약관 보기</a></b>
                                                            </span>
                                                        </span>
                                                    </span>
                                                </div>
                                            </div>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="row">
                    <div className="col col-12">
                        <div className="wrap-section wrap-tbl">
                            <div className="box-body">
                                <div className="tbl">
                                    <dl className="colum-two">
                                        <dt>
                                            <div className="dt-inner">
                                    <span className="fz-16 fc-1">
                                        충전사업자번호
                                    </span>
                                            </div>
                                        </dt>
                                        <dd>
                                            <div className="form-group">
                                                <select
                                                    className="select2-single w-300 select2-hidden-accessible"
                                                    tabIndex={-1} aria-hidden="true">
                                                    <option></option>
                                                    <option>옵션1</option>
                                                    <option>옵션2</option>
                                                    <option>옵션3</option>
                                                </select><span
                                                className="select2 select2-container select2-container--default"
                                                dir="ltr" style={{width: '300px'}}><span
                                                className="selection"><span
                                                className="select2-selection select2-selection--single"
                                                role="combobox" aria-haspopup="true" aria-expanded="false"
                                                tabIndex={0} aria-labelledby="select2-m1q3-container"><span
                                                className="select2-selection__rendered"
                                                id="select2-m1q3-container"><span
                                                className="select2-selection__placeholder">셀렉트2 싱글</span></span><span
                                                className="select2-selection__arrow" role="presentation"><b
                                                role="presentation"></b></span></span></span><span
                                                className="dropdown-wrapper" aria-hidden="true"></span></span>
                                                <select
                                                    className="select2-single w-300 select2-hidden-accessible"
                                                    disabled={false} tabIndex={-1} aria-hidden="true">
                                                    <option></option>
                                                    <option>옵션1</option>
                                                    <option>옵션2</option>
                                                    <option>옵션3</option>
                                                </select><span
                                                className="select2 select2-container select2-container--default select2-container--disabled"
                                                dir="ltr" style={{width: '300px'}}><span
                                                className="selection"><span
                                                className="select2-selection select2-selection--single"
                                                role="combobox" aria-haspopup="true" aria-expanded="false"
                                                tabIndex={-1} aria-labelledby="select2-a3pc-container"><span
                                                className="select2-selection__rendered"
                                                id="select2-a3pc-container"><span
                                                className="select2-selection__placeholder">셀렉트2 싱글</span></span><span
                                                className="select2-selection__arrow" role="presentation"><b
                                                role="presentation"></b></span></span></span><span
                                                className="dropdown-wrapper" aria-hidden="true"></span></span>
                                                <button type="button" className="btn btn-primary">저장</button>
                                            </div>
                                            <div className="form-group">
                                                <p className="comp-txt">
                                        <span className="table">
                                            <span className="table-cell">
                                                <b className="fz-12 fc-3 lh20"><i className="fz-12 fc-5">*</i>선택한 사업자번호를 기준으로 비즈머니가 관리됩니다.</b>
                                                <b className="fz-12 fc-3 lh20"><i className="fz-12 fc-5">*</i>사업자번호 변경조건<a
                                                    className="ico-tooltip tooltip-f" title=""></a></b>
                                            </span>
                                        </span>
                                                </p>
                                            </div>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="row flex-container">
                    <div className="col col-6">
                        <div className="wrap-section wrap-tbl">
                            <div className="box-header">
                                <div className="box-tit">
                                    <h2 className="fz-20 fc-1 fw-bold">알림설정</h2>
                                </div>
                            </div>
                            <div className="box-header">
                                <div className="box-tit">
                                    <h2 className="fz-18 fc-1">알림 수신동의</h2>
                                </div>
                                <div className="box-option">
                                    <div className="comp-switch">
                                        <input type="checkbox" id="switch-01" checked={true}/>
                                        <label htmlFor="switch-01">
                                            <i className="ico"></i>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div className="box-body">
                                <div className="tbl">
                                    <dl className="vertical">
                                        <dt>
                                            <div className="dt-inner">
                                    <span className="fz-16 fc-1">
                                        충전사업자번호
                                    </span>
                                            </div>
                                        </dt>
                                        <dd>
                                            <div className="form-group">
                                                <div className="comp-checkbox block">
                                                    <input type="checkbox" id="inp-check-01" checked={true}/>
                                                    <label htmlFor="inp-check-01">캠페인 알림 수신</label>
                                                </div>
                                                <div className="comp-checkbox block">
                                                    <input type="checkbox" id="inp-check-02" checked={true}/>
                                                    <label htmlFor="inp-check-02">프로모션 알림 수신</label>
                                                </div>
                                                <div className="comp-checkbox block">
                                                    <input type="checkbox" id="inp-check-03" checked={true}/>
                                                    <label htmlFor="inp-check-03">비즈머니 충전 알림 수신</label>
                                                </div>
                                                <div className="comp-checkbox block">
                                                    <input type="checkbox" id="inp-check-04"/>
                                                    <label htmlFor="inp-check-04">야간 알림 수신 (21시 ~ 익일 8시)</label>
                                                </div>
                                            </div>
                                            <div className="form-group">
                                                        <span className="comp-txt">
                                        <span className="table">
                                            <span className="table-cell">
                                                <b className="fz-12 fc-3 lh20"><i className="fz-12 fc-5">*</i>의무적으로 안내되어야 하는 정보성 내용은 수신동의 여부와 무관하게 제공합니다.</b>
                                            </span>
                                        </span>
                                    </span>
                                            </div>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="col col-6">
                        <div className="wrap-section wrap-tbl">
                            <div className="box-header">
                                <div className="box-tit">
                                    <h2 className="fz-20 fc-1 fw-bold">환불계좌</h2>
                                </div>
                            </div>
                            <div className="box-header">
                                <div className="box-tit">
                                    <h2 className="fz-18 fc-1">하나은행</h2>
                                    <span className="fz-18 fc-1">123-45-6789-012</span>
                                </div>
                                <div className="box-option">
                                    <button type="button" className="btn btn btn-primary">변경</button>
                                </div>
                            </div>
                            <div className="box-body">
                                <div className="tbl">
                                    <dl className="vertical">
                                        <dd>
                                            <div className="form-group">
                                                <div className="comp-help">
                                                    <ul className="help-list">
                                                        <li className="list">
                                                            <i className="ico ico-help"></i><span
                                                            className="fz-14 fc-2">환불 주의사항</span>
                                                        </li>
                                                        <li className="list">
                                                            <span className="fz-14 fc-4 bullet">진행중인 캠페인이 있으면 환불 신청이 불가능합니다.</span>
                                                        </li>
                                                        <li className="list">
                                                            <span className="fz-14 fc-4 bullet">캠페인 정산이 모두 완료되어야 환불 신청이 가능합니다.</span>
                                                        </li>
                                                        <li className="list">
                                                            <span className="fz-14 fc-4 bullet">환불을 신청하면 유상비즈머니 잔액이 모두 환불됩니다.</span>
                                                        </li>
                                                        <li className="list">
                                                            <span className="fz-14 fc-4 bullet">유상비즈머니를 충전한 방식 그대로 환불됩니다.</span>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                            <div className="box-footer">
                                <div className="box-right">
                                    <button type="button" className="btn btn-primary-outline">환불내역</button>
                                    <button type="button" className="btn btn btn-danger-outline">환불신청</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="row flex-container">
                    <div className="col col-6">
                        <div className="wrap-section wrap-tbl">
                            <div className="box-header">
                                <div className="box-tit">
                                    <h2 className="fz-20 fc-1 fw-bold">알림설정</h2>
                                </div>
                            </div>
                            <div className="box-header">
                                <div className="box-tit">
                                    <h2 className="fz-18 fc-1">알림 수신동의</h2>
                                </div>
                                <div className="box-option">
                                    <div className="comp-switch">
                                        <input type="checkbox" id="switch-02"/>
                                        <label htmlFor="switch-02">
                                            <i className="ico"></i>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div className="box-body">
                                <div className="tbl">
                                    <dl className="vertical">
                                        <dt>
                                            <div className="dt-inner">
                                    <span className="fz-16 fc-1">
                                        충전사업자번호
                                    </span>
                                            </div>
                                        </dt>
                                        <dd>
                                            <div className="form-group">
                                                <div className="comp-checkbox block">
                                                    <input type="checkbox" id="inp-check-05" disabled={false}/>
                                                    <label htmlFor="inp-check-01">캠페인 알림 수신</label>
                                                </div>
                                                <div className="comp-checkbox block">
                                                    <input type="checkbox" id="inp-check-06" disabled={false}/>
                                                    <label htmlFor="inp-check-02">프로모션 알림 수신</label>
                                                </div>
                                                <div className="comp-checkbox block">
                                                    <input type="checkbox" id="inp-check-07" disabled={false}/>
                                                    <label htmlFor="inp-check-03">비즈머니 충전 알림 수신</label>
                                                </div>
                                                <div className="comp-checkbox block">
                                                    <input type="checkbox" id="inp-check-08" disabled={false}/>
                                                    <label htmlFor="inp-check-04">야간 알림 수신 (21시 ~ 익일
                                                        8시)</label>
                                                </div>
                                            </div>
                                            <div className="form-group">
                                                        <span className="comp-txt">
                                        <span className="table">
                                            <span className="table-cell">
                                                <b className="fz-12 fc-3 lh20"><i className="fz-12 fc-5">*</i>의무적으로 안내되어야 하는 정보성 내용은 수신동의 여부와 무관하게 제공합니다.</b>
                                            </span>
                                        </span>
                                    </span>
                                            </div>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="col col-6">
                        <div className="wrap-section wrap-tbl">
                            <div className="box-header">
                                <div className="box-tit">
                                    <h2 className="fz-20 fc-1 fw-bold">환불계좌</h2>
                                </div>
                            </div>
                            <div className="box-header">
                                <div className="box-tit">
                                    <h2 className="fz-18 fc-1">등록된 환불계좌가 없습니다.</h2>
                                </div>
                                <div className="box-option">
                                    <button type="button" className="btn btn btn-primary">등록</button>
                                </div>
                            </div>
                            <div className="box-body">
                                <div className="tbl">
                                    <dl className="vertical">
                                        <dd>
                                            <div className="form-group">
                                                <div className="comp-help">
                                                    <ul className="help-list">
                                                        <li className="list">
                                                            <i className="ico ico-help"></i><span
                                                            className="fz-14 fc-2">환불 주의사항</span>
                                                        </li>
                                                        <li className="list">
                                                            <span className="fz-14 fc-4 bullet">진행중인 캠페인이 있으면 환불 신청이 불가능합니다.</span>
                                                        </li>
                                                        <li className="list">
                                                            <span className="fz-14 fc-4 bullet">캠페인 정산이 모두 완료되어야 환불 신청이 가능합니다.</span>
                                                        </li>
                                                        <li className="list">
                                                            <span className="fz-14 fc-4 bullet">환불을 신청하면 유상비즈머니 잔액이 모두 환불됩니다.</span>
                                                        </li>
                                                        <li className="list">
                                                            <span className="fz-14 fc-4 bullet">유상비즈머니를 충전한 방식 그대로 환불됩니다.</span>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                            <div className="box-footer">
                                <div className="box-right">
                                    <button type="button" className="btn btn-primary-outline">환불내역</button>
                                    <button type="button" className="btn btn btn-danger-outline">환불신청</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ContentBody;