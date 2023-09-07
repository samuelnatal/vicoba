const { pool } = require("../database");

function getKikobaInfo(req, res) {
  var { kikobaID } = req.body;
  pool.query(
    'SELECT region, ward, district, "kikobaID", "kikobaName", "kikobaDesc", "kikobaWallet", TO_CHAR(kikoba."createdAt",\'YYYY-MM-DD\') AS "createdAt","sharePrice", "maxShare","shareCircle", "firstName", "lastName", email, phone, member."userKey" FROM "address" JOIN "kikoba" ON "addressID" = "kikobaLocationID" JOIN "admin" ON "kikobaID" = "kikobaKey" JOIN "member" ON "memberKey" = "memberID" JOIN "user" ON "userKey" = "userID" WHERE "kikobaID" = ' +
      kikobaID +
      ";",
    (error, activeKikoba) => {
      if (error) {
        throw error;
      }

      //Query to retrieve secretary of kikoba group
      pool.query(
        'SELECT "userID", "firstName", "lastName" FROM "secretary" JOIN "member" ON "memberKey" = "memberID" JOIN "user" ON "userKey" = "userID" WHERE secretary."kikobaKey" = ' +
          kikobaID +
          ";",
        (error, secretary) => {
          if (error) {
            throw error;
          }

          var secretaryID, secretaryName;

          if (secretary.rows.length == 0) {
            secretaryID = 0;
            secretaryName = "Hakuna katibu kwa sasa.";
          } else {
            secretaryID = secretary.rows[0].userID;
            secretaryName = secretary.rows[0].firstName+" "+secretary.rows[0].lastName;
          }

          //Query to retrieve accountant of kikoba group.
          pool.query(
            'SELECT "userID", "firstName", "lastName" FROM "accountant" JOIN "member" ON "memberKey" = "memberID" JOIN "user" ON "userKey" = "userID" WHERE accountant."kikobaKey" = ' +
              kikobaID +
              ";",
            (error, accountant) => {
              if (error) {
                throw error;
              }
              var accountantID, accountantName;

              if (accountant.rows.length == 0) {
                accountantID = 0;
                accountantName = "Hakuna mhasibu kwa sasa.";
              } else {
                accountantID = accountant.rows[0].userID;
                accountantName = accountant.rows[0].firstName+" "+accountant.rows[0].lastName;
              }

              //Query to retrieve chair person of kikoba app.
              pool.query(
                'SELECT "userID", "firstName", "lastName" FROM "chair_person" JOIN "member" ON "memberKey" = "memberID" JOIN "user" ON "userKey" = "userID" WHERE chair_person."kikobaKey" = ' +
                  kikobaID +
                  ";",
                (error, chairPerson) => {
                  if (error) {
                    throw error;
                  }

                  var chairPersonID, chairPersonName;

                  if (chairPerson.rows.length == 0) {
                    chairPersonID = 0;
                    chairPersonName = "Hakuna mwenyekiti kwa sasa.";
                  } else {
                    chairPersonID = chairPerson.rows[0].userID;
                    chairPersonName = chairPerson.rows[0].firstName+" "+chairPerson.rows[0].lastName;
                  }

                  //A variable that holds full information of the active kikoba retrieved from the database to be returned to the vicoba app
                  var activeKikobaResponse = {
                    region: activeKikoba.rows[0].region,
                    ward: activeKikoba.rows[0].ward,
                    district: activeKikoba.rows[0].district,
                    kikobaID: activeKikoba.rows[0].kikobaID,
                    kikobaName: activeKikoba.rows[0].kikobaName,
                    kikobaDesc: activeKikoba.rows[0].kikobaDesc,
                    kikobaWallet: activeKikoba.rows[0].kikobaWallet,
                    createdAt: activeKikoba.rows[0].createdAt,
                    sharePrice: activeKikoba.rows[0].sharePrice,
                    maxShare: activeKikoba.rows[0].maxShare,
                    shareCircle: activeKikoba.rows[0].shareCircle,
                    firstName: activeKikoba.rows[0].firstName,
                    lastName: activeKikoba.rows[0].lastName,
                    email: activeKikoba.rows[0].email,
                    phone: activeKikoba.rows[0].phone,
                    kikobaAdmin: activeKikoba.rows[0].userKey,
                    kikobaSecretaryID: secretaryID,
                    kikobaAccountantID: accountantID,
                    kikobaChairPersonID: chairPersonID,
                    kikobaSecretaryName: secretaryName,
                    kikobaAccountantName: accountantName,
                    kikobaChairPersonName: chairPersonName,
                  };

                  res.json(activeKikobaResponse);
                }
              );
            }
          );
        }
      );
    }
  );
}
module.exports = { getKikobaInfo };
