const { pool } = require("../database");

function saveNewKikoba(req, res) {
  var { kikobaName, kikobaDesc, kikobaOwnerID, kikobaLocationID } = req.body;

  // Query to store new data of the created kikoba
  pool.query(
    'INSERT INTO "kikoba"  ("kikobaName","kikobaDesc","kikobaOwnerID","kikobaLocationID") VALUES (\'' +
      kikobaName +
      "','" +
      kikobaDesc +
      "','" +
      kikobaOwnerID +
      "','" +
      parseInt(kikobaLocationID.substring(0, 1)) +
      "');",
    (error, results) => {
      if (error) {
        throw error;
      }

      //Query to retrieve newly created kikoba ID and name to be used by other queries.
      pool.query(
        'SELECT "kikobaID", "kikobaName" FROM "kikoba" ORDER BY "kikobaID" DESC LIMIT 1;',
        (error, newKikoba) => {
          if (error) {
            throw error;
          }

          //Query to add kikoba owner as the first member of the kikoba
          pool.query(
            'INSERT INTO "member"  ("userKey","kikobaKey","status") VALUES (\'' +
              kikobaOwnerID +
              "'," +
              newKikoba.rows[0].kikobaID +
              ",'Approved');",
            (error, results) => {
              if (error) {
                throw error;
              }

              //Query to retrieve an member ID of the creator of kikoba to be inserted into an admin table
              pool.query(
                'SELECT "memberID" FROM "member" ORDER BY "memberID" DESC LIMIT 1;',
                (error, members) => {
                  if (error) {
                    throw error;
                  }

                  //Query to add the creator of the kikoba as an initial admin.
                  pool.query(
                    'INSERT INTO "admin" VALUES (' +
                      newKikoba.rows[0].kikobaID +
                      "," +
                      members.rows[0].memberID +
                      ");",
                    (error, results) => {
                      if (error) {
                        throw error;
                      }
                    }
                  );
                }
              );
            }
          );

          //Query to save new notification after succefful creating new kikoba
          pool.query(
            'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody", "kikobaInvolved") VALUES (' +
              kikobaOwnerID +
              ",'Kikoba.','Umefanikiwa kufungua kikoba kipya kwa jina la \"" +
              newKikoba.rows[0].kikobaName +
              "\".'," +
              newKikoba.rows[0].kikobaID +
              ");",
            (error, results) => {
              if (error) {
                throw error;
              }
            }
          );

          //Query to save new notification after succefful creating new kikoba
          pool.query(
            'SELECT region, ward, district, "kikobaID", "kikobaName", "kikobaDesc", "kikobaWallet", TO_CHAR(kikoba."createdAt",\'YYYY-MM-DD\') AS "createdAt", "sharePrice", "maxShare", "shareCircle", "firstName", "lastName", email, phone, member."userKey" FROM "address" JOIN "kikoba" ON "addressID" = "kikobaLocationID" JOIN "admin" ON "kikobaID" = "kikobaKey" JOIN "member" ON "memberKey" = "memberID" JOIN "user" ON "userKey" = "userID" ORDER BY "kikobaID" DESC LIMIT 1;',
            (error, activeKikoba) => {
              if (error) {
                throw error;
              }

              //Query to retrieve secretary of kikoba.
              pool.query(
                'SELECT "userID", "firstName", "lastName" FROM "secretary" JOIN "member" ON "memberKey" = "memberID" JOIN "user" ON "userKey" = "userID"WHERE secretary."kikobaKey" = ' +
                  activeKikoba.rows[0].kikobaID +
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

                  //Query to retrieve accountant of kikoba.
                  pool.query(
                    'SELECT "userID", "firstName", "lastName" FROM "accountant" JOIN "member" ON "memberKey" = "memberID" JOIN "user" ON "userKey" = "userID"WHERE accountant."kikobaKey" = ' +
                      activeKikoba.rows[0].kikobaID +
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
        

                      //Query to retrieve a chair person of kikoba.
                      pool.query(
                        'SELECT "userID", "firstName", "lastName" FROM "chair_person" JOIN "member" ON "memberKey" = "memberID" JOIN "user" ON "userKey" = "userID"WHERE chair_person."kikobaKey" = ' +
                          activeKikoba.rows[0].kikobaID +
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

                          //Active kikoba object to be returned to the app
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
      );
    }
  );
}

module.exports = { saveNewKikoba };
