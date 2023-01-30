interface DashboardBox {
    title: string,
    content: string,
    icon: boolean
}

const DashboardBox = (props: DashboardBox) => {
    return (
        <div className="col col-4">
            <div className="wrap-section wrap-tbl wrap-dashboard">
                <div className="box-body">
                    <div className="tbl">
                        <dl className="vertical">
                            <dt>
                                <div className="dt-inner">
                                    <span className="fz-14 fc-4">
                                        {props.title}
                                        {props.icon ? <a className="ico-tooltip tooltip-f" title=""></a> : null}
                                    </span>
                                </div>
                            </dt>
                            <dd>
                                <div className="form-group">
                                    <span className="comp-txt">
                                        <span className="table">
                                            <span className="table-cell">
                                                <b className="fz-20 fc-2 fw-medium">{props.content}</b>
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
    )
}

export default DashboardBox;