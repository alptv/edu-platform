import "./Popup.css"


export enum PopupType {
    NONE,
    SUCCESS,
    FAILURE
}

export function fromBoolean(indicator: boolean) {
    if (indicator) {
        return PopupType.SUCCESS
    } else {
        return PopupType.FAILURE
    }
}

export function createPopupFactory(successMessage: string, failureMessage: string, setPopupType: (_: PopupType) => void, additionalActions?: () => void) {
    return (popupType: PopupType) => {
        switch (popupType) {
            case PopupType.SUCCESS:
                return <Popup additionalActions={additionalActions} message={successMessage}
                              setPopupType={setPopupType}/>
            case PopupType.FAILURE:
                return <Popup additionalActions={additionalActions} message={failureMessage}
                              setPopupType={setPopupType}/>
            case PopupType.NONE:
                return <></>
        }
    }
}

export const Popup = (props: {
    message: string,
    setPopupType: (popupType: PopupType) => void
    additionalActions?: () => void
}) => (
    <div className="Popup">
        <div className="PopupContent">
            <div className="DeleteForm">
                <button onClick={() => {
                    props.setPopupType(PopupType.NONE)
                    if (props.additionalActions) {
                        props.additionalActions()
                    }
                }} className="DeleteButton">
                    &#10060;
                </button>
            </div>
            <div className="PopupMessage">
                {props.message}
            </div>
        </div>
    </div>
)