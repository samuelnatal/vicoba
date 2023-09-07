package com.example.vicoba.data.network

import com.example.vicoba.data.models.entities.ActiveKikoba
import com.example.vicoba.data.models.entities.ActiveKikobaUser
import com.example.vicoba.data.models.entities.EditedKikobaProfile
import com.example.vicoba.data.models.entities.InvitedKikoba
import com.example.vicoba.data.models.entities.KikobaRequestCredentials
import com.example.vicoba.data.models.entities.NewKikoba
import com.example.vicoba.data.models.entities.NewKikobaUser
import com.example.vicoba.data.models.entities.UserLoginCredentials
import com.example.vicoba.data.models.items.DebtCoupon
import com.example.vicoba.data.models.items.InvitationCoupon
import com.example.vicoba.data.models.items.KIDsearchItem
import com.example.vicoba.data.models.items.LoanCoupon
import com.example.vicoba.data.models.items.SearchItem
import com.example.vicoba.data.models.items.ShareCoupon
import com.example.vicoba.data.models.items.TotalKikobaMembers
import com.example.vicoba.data.models.keys.AccountantLoanKey
import com.example.vicoba.data.models.keys.ChairPersonLoanKey
import com.example.vicoba.data.models.keys.KikobaID
import com.example.vicoba.data.models.keys.KikobaIDUserID
import com.example.vicoba.data.models.keys.KikobaLeaderID
import com.example.vicoba.data.models.keys.LoanID
import com.example.vicoba.data.models.keys.MemberID
import com.example.vicoba.data.models.keys.NotificationID
import com.example.vicoba.data.models.keys.SecretaryLoanKey
import com.example.vicoba.data.models.keys.UserID
import com.example.vicoba.data.models.keys.UserIDAddressID
import com.example.vicoba.data.models.lists.Address
import com.example.vicoba.data.models.lists.Debt
import com.example.vicoba.data.models.lists.GeneralSearchResult
import com.example.vicoba.data.models.lists.KikobaMember
import com.example.vicoba.data.models.lists.Loan
import com.example.vicoba.data.models.lists.LoanSummary
import com.example.vicoba.data.models.lists.Occupation
import com.example.vicoba.data.models.lists.Share
import com.example.vicoba.data.models.lists.UserNotification
import com.example.vicoba.data.models.lists.UserRequestedKikoba
import com.example.vicoba.data.models.lists.UserSearchedToAddInKikoba
import com.example.vicoba.data.models.lists.VikobaNearUser
import com.example.vicoba.data.models.lists.VikobaUserInvolvedIn
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Retrofit service object for creating api calls for internet vicoba data source
 */
interface VicobaApiService {

    /** END POINTS TARGETED FOR OCCUPATIONS DATA OPERATIONS */
    @GET("occupations")
    suspend fun getOccupations(): List<Occupation>

    /** END POINTS TARGETED FOR ADDRESS DATA OPERATIONS IN A DATRABASE */
    @GET("addresses")
    suspend fun getAddresses(): List<Address>

    /** END POINTS TARGETED FOR USER DATA OPERATIONS */
    @POST("user/register")
    suspend fun registerUser(@Body user: NewKikobaUser): ActiveKikobaUser

    @POST("user/login")
    suspend fun loginUser(@Body user: UserLoginCredentials): ActiveKikobaUser

    @POST("user/vikoba/joined")
    suspend fun getVikobaUserInvolvedIn(@Body userID: UserID): List<VikobaUserInvolvedIn>

    @POST("user/vikoba/near")
    suspend fun getVikobaNearUser(@Body userIDAddressID: UserIDAddressID): List<VikobaNearUser>

    @POST("user/notifications")
    suspend fun getUserNotifications(@Body userID: UserID): List<UserNotification>

    @POST("user/kikoba/request/info")
    suspend fun getUserRequestedKikobaInfo(@Body kikobaIDUserID: KikobaIDUserID): UserRequestedKikoba
    @POST("user/kikoba/invitation/accept")
    suspend fun acceptKikobaInvitation(@Body invitationCoupon: InvitationCoupon): Boolean

    @POST("user/kikoba/invitation/reject")
    suspend fun rejectKikobaInvitation(@Body invitationCoupon: InvitationCoupon): Boolean

    /** END POINTS TARGETED FOR VIKOBA DATA OPERATIONS */
    @POST("vicoba/new")
    suspend fun saveNewKikoba(@Body kikoba: NewKikoba): ActiveKikoba

    @POST("kikoba/info")
    suspend fun getKikobaInfo(@Body kikobaID: KikobaID): ActiveKikoba

    @POST("kikoba/users/requests")
    suspend fun getUsersKikobaRequests(@Body kikobaID: KikobaID): List<UserRequestedKikoba>

    @POST("kikoba/request/save")
    suspend fun saveKikobaJoinRequest(@Body KikobaReqCredentials: KikobaRequestCredentials): Boolean

    @POST("kikoba/request/approve")
    suspend fun approveKikobaJoinRequest(@Body kikobaIDUserID: KikobaIDUserID): Boolean

    @POST("kikoba/request/cancel")
    suspend fun cancelKikobaJoinRequest(@Body kikobaIDUserID: KikobaIDUserID): Boolean

    @POST("kikoba/invite")
    suspend fun inviteUserToJoinKikoba(@Body kikobaIDUserID: KikobaIDUserID): Boolean

    @POST("kikoba/members")
    suspend fun getKikobaMembers(@Body kikobaID: KikobaID): List<KikobaMember>
   
    @POST("kikoba/profile/update")
    suspend fun updateKikobaProfile(@Body editedKikobaProfile: EditedKikobaProfile): Boolean
    @POST("kikoba/members/total")
    suspend fun getTotalKikobaMembers(@Body kikobaID: KikobaID): TotalKikobaMembers

    @POST("kikoba/invited/info")
    suspend fun getInvitedKikobaInfo(@Body kikobaID: KikobaID): InvitedKikoba
    @POST("kikoba/members/remove")
    suspend fun removeKikobaMember(@Body kikobaIDUserID: KikobaIDUserID): Boolean

    @POST("kikoba/admin/select")
    suspend fun selectKikobaAdmin(@Body kikobaLeaderID: KikobaLeaderID): Boolean
    @POST("kikoba/secretary/select")
    suspend fun selectKikobaSecretary(@Body kikobaLeaderID: KikobaLeaderID): Boolean
    @POST("kikoba/accountant/select")
    suspend fun selectKikobaAccountant(@Body kikobaLeaderID: KikobaLeaderID): Boolean
    @POST("kikoba/chairperson/select")
    suspend fun selectKikobaChairPerson(@Body kikobaLeaderID: KikobaLeaderID): Boolean

    /** END POINTS TARGETED FOR DATA SEARCH OPERATIONS */
    @POST("search/general")
    suspend fun searchGeneral(@Body searchItem: SearchItem): List<GeneralSearchResult>

    @POST("search/user/to-add-in-kikoba")
    suspend fun searchUserToAddInKikoba(@Body kIDsearchItem : KIDsearchItem): List<UserSearchedToAddInKikoba>

    /** END POINTS TARGETED FOR NOTIFICATION OPERATION */
    @POST("notification/delete")
    suspend fun deleteViewedNotification(@Body notificationID: NotificationID): Boolean


    /** END POINTS TARGETED FOR KIKOBA MEMBER OPERATIONS */
    @POST("member/id")
    suspend fun getKikobaMemberID(@Body kikobaIDUserID: KikobaIDUserID): Int
    @POST("member/loan/request")
    suspend fun requestLoan(@Body loanCoupon: LoanCoupon): Boolean
    @POST("member/loans")
    suspend fun getMyLoans(@Body memberID: MemberID): List<LoanSummary>
    @POST("members/loans")
    suspend fun getMembersLoans(@Body kikobaID: KikobaID): List<LoanSummary>


    /** END POINTS TARGETED FOR LOAN OPERATIONS */
    @POST("loan/info")
    suspend fun getLoanInfo(@Body loanID: LoanID): Loan
    @POST("loan/reject")
    suspend fun rejectLoan(@Body secretaryLoanKey: SecretaryLoanKey): Boolean
    @POST("loan/accept")
    suspend fun acceptLoan(@Body secretaryLoanKey: SecretaryLoanKey): Boolean
    @POST("loan/approve")
    suspend fun approveLoan(@Body chairPersonLoanKey: ChairPersonLoanKey): Boolean
    @POST("loan/pay")
    suspend fun payLoan(@Body accountantLoanKey: AccountantLoanKey): Boolean

    /** END POINTS TARGETED FOR SHARE OPERATIONS */
    @POST("share/buy")
    suspend fun buyShare(@Body shareCoupon: ShareCoupon): Boolean
    @POST("shares/all")
    suspend fun getAllShares(@Body kikobaID: KikobaID): List<Share>

    /** END POINTS TARGETED FOR SHARE OPERATIONS */
    @POST("debt/pay")
    suspend fun payDebt(@Body debtCoupon: DebtCoupon): Boolean
    @POST("debts/all")
    suspend fun getAllDebts(@Body kikobaID: KikobaID): List<Debt>
}
