const express = require("express");
const app = express();
const bodyParser = require("body-parser");

//End points importation required by the server
const { login } = require("./kikoba_user_apis/login");
const { register } = require("./kikoba_user_apis/registration");
const { getAddresses } = require("./app_data_apis/addresses");
const { getOccupations } = require("./app_data_apis/occupations");
const { saveNewKikoba } = require("./kikoba_apis/saveNewKikoba");
const { getVikobaUserInvolvedIn } = require("./kikoba_user_apis/vikobaUserInvolvedIn");
const { getVikobaNearUser } = require("./kikoba_user_apis/vikobaNearUser");
const { getKikobaInfo } = require("./kikoba_apis/kikobaInfo");
const { saveKikobaJoinRequest } = require("./kikoba_user_apis/kikobaJoinRequest");
const { getUsersKikobaRequests } = require("./kikoba_admin_apis/usersKikobaRequests");
const { approveKikobaJoinRequest } = require("./kikoba_admin_apis/approveKikobaJoinRequest");
const { cancelKikobaJoinRequest } = require("./kikoba_admin_apis/cancelKikobaJoinRequest");
const { getUserNotifications } = require("./kikoba_user_apis/notifications");
const { generalSearch } = require("./search_apis/generalSearch");
const { getUserRequestedKikobaInfo } = require("./kikoba_admin_apis/userRequestedKikobaInfo");
const { searchUserToAddInKikoba } = require("./search_apis/searchUserToAddInKikoba");
const { inviteUserToJoinKikoba } = require("./kikoba_admin_apis/inviteUserToJoinKikoba");
const { getInvitedKikobaInfo } = require("./kikoba_apis/InvitedKikobaInfo");
const { getKikobaMembers } = require("./kikoba_apis/kikobaMembers");
const { getTotalKikobaMembers } = require("./kikoba_apis/totalKikobaMembers");
const { acceptKikobaInvitation } = require("./kikoba_user_apis/acceptKikobaInvitation");
const { rejectKikobaInvitation } = require("./kikoba_user_apis/rejectKikobaInvitation");
const { removeKikobaMember } = require("./kikoba_admin_apis/removeKikobaMember");
const { updateKikobaProfile } = require("./kikoba_apis/updateKikobaProfile");
const { selectAdmin } = require("./kikoba_apis/selectAdmin");
const { selectSecretary } = require("./kikoba_apis/selectSecretary");
const { selectAccountant } = require("./kikoba_apis/selectAccountant");
const { selectChairPerson } = require("./kikoba_apis/selectChairPerson");
const { deleteViewedNotification } = require("./notification_apis/deleteViewedNotofication");
const { getKikobaMemberID } = require("./member_apis/getKikobaMemberID");
const { requestLoan } = require("./member_apis/requestLoan");
const { getMyLoans } = require("./member_apis/getMyLoans");
const { getMembersLoans } = require("./member_apis/getMembersLoans");
const { getLoanInfo } = require("./secretary_apis/getLoanInfo");
const { rejectLoan } = require("./secretary_apis/rejectLoan");
const { acceptLoan } = require("./secretary_apis/acceptLoan");
const { approveLoan } = require("./chair_person_apis/approveLoan");
const { payLoan } = require("./accountant_apis/payLoan");
const { buyShare } = require("./member_apis/buyShare");
const { getAllShares } = require("./member_apis/getAllShares");
const { getAllDebts } = require("./member_apis/getAllDebts");
const { payDebt } = require("./member_apis/payDebt");

// parse application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: false }));

// parse application/json
app.use(bodyParser.json());

//VICOBA API end point for inserting new kikoba information
app.post("/vicoba/new", saveNewKikoba);

//VICOBA API end point for getting a list of occupations required by Vicoba App
app.get("/occupations", getOccupations);

//VICOBA API end point for getting a list of addresses required by Vicoba App
app.get("/addresses", getAddresses);

//VICOBA API End point for authenticating user for login
app.post("/user/login", login);

//VICOBA API End point for serving user kikoba account creation
app.post("/user/register", register);

//VICOBA API End point for retrieving a list of vicoba user is involved in
app.post("/user/vikoba/joined", getVikobaUserInvolvedIn);

//VICOBA API End point for retrieving a list of vicoba near to the user
app.post("/user/vikoba/near", getVikobaNearUser);

//VICOBA API End point for retrieving information of the single kikoba
app.post("/kikoba/info", getKikobaInfo);

//VICOBA API End point for requesting to join kikoba
app.post("/kikoba/request/save", saveKikobaJoinRequest);

//VICOBA API End point for retrieving a list of user requests for a paricular kikoba
app.post("/kikoba/users/requests", getUsersKikobaRequests);

//VICOBA API End point for approving requests to join particular kikoba
app.post("/kikoba/request/approve", approveKikobaJoinRequest);

//VICOBA API End point for rejecting/cancelling requests to join particular kikoba
app.post("/kikoba/request/cancel", cancelKikobaJoinRequest);

//VICOBA API End point for retrieving user notifications.
app.post("/user/notifications", getUserNotifications);

//VICOBA API End point for searching users ang vikoba available from vicoba app database.
app.post("/search/general", generalSearch);

//VICOBA API End point for retrieving information of user who requested kikoba.
app.post("/user/kikoba/request/info", getUserRequestedKikobaInfo);

//VICOBA API End point for searching user to add in kikoba.
app.post("/search/user/to-add-in-kikoba", searchUserToAddInKikoba);

//VICOBA API End point for inviting user to join kikoba
app.post("/kikoba/invite", inviteUserToJoinKikoba);

//VICOBA API End point for retrieving information of kikoba in which user have been invited for
app.post("/kikoba/invited/info", getInvitedKikobaInfo);

//VICOBA API End point for updating details of the existing kikoba
app.post("/kikoba/profile/update", updateKikobaProfile);

//VICOBA API End point for retrieving a list of members basic information participationg in a particular kikoba
app.post("/kikoba/members", getKikobaMembers);

//VICOBA API End point for retrieving a list of members basic information participationg in a particular kikoba
app.post("/kikoba/members/total", getTotalKikobaMembers);

//VICOBA API End point for accepting kikoba invitation by a user.
app.post("/user/kikoba/invitation/accept", acceptKikobaInvitation);

//VICOBA API End point for rejecting kikoba invitation by a user.
app.post("/user/kikoba/invitation/reject", rejectKikobaInvitation);

//VICOBA API End point for removing kikoba member from a particular kikoba group.
app.post("/kikoba/members/remove", removeKikobaMember);

//VICOBA API End point for selecting new admin of kikoba app.
app.post("/kikoba/admin/select", selectAdmin);

//VICOBA API End point for adding new kikoba secretaty
app.post("/kikoba/secretary/select", selectSecretary);

//VICOBA API End point for adding new kikoba accountant.
app.post("/kikoba/accountant/select", selectAccountant);

//VICOBA API End point for adding new kikoba chair person
app.post("/kikoba/chairperson/select", selectChairPerson);

//VICOBA API End point for deleting viewed notification
app.post("/notification/delete", deleteViewedNotification);

//VICOBA API End point for retrieving member ID of the particular kikoba
app.post("/member/id", getKikobaMemberID);

//VICOBA API End point for retrieving member ID of the particular kikoba
app.post("/member/loan/request", requestLoan);

//VICOBA API End point for rtrieveng loans of specific member of kikoba
app.post("/member/loans", getMyLoans);

//VICOBA API End point for getting full information of a particular loan
app.post("/loan/info", getLoanInfo);

//VICOBA API End point for rejecting loan request by a secretary
app.post("/loan/reject", rejectLoan);

//VICOBA API End point for accepting loan request by a secretary
app.post("/loan/accept", acceptLoan);

//VICOBA API End point for approving loan request by a chairperson.
app.post("/loan/approve", approveLoan);

//VICOBA API End point for paying loan by an accountant.
app.post("/loan/pay", payLoan);

//VICOBA API End point for retrieving all loans requested by member of a particular kikoba.
app.post("/members/loans", getMembersLoans);

//VICOBA API End point for member to buy share.
app.post("/share/buy", buyShare);

//VICOBA API End point for retrieving all shares made by members.
app.post("/shares/all", getAllShares);

//VICOBA API End point for retrieving all debts made by members.
app.post("/debts/all", getAllDebts);

//VICOBA API End point for paying a debt by kikoba member.
app.post("/debt/pay", payDebt);

//Setting the port 3000 for the postgresql database server to listen
app.listen(3000, () => {
  console.log("Server is running on port 3000");
});
