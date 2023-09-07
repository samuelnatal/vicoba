const { pool } = require("../database");

function requestLoan(req, res) {
  var {
    borrowerID,
    loanAmount,
    loanDesc,
    guarantee,
    guarantor1ID,
    guarantor2ID,
    guarantor3ID,
    kikobaKey
  } = req.body;

  var pguarantor1ID = parseInt(guarantor1ID.substring(0,guarantor1ID.indexOf('.')));
  var pguarantor2ID = parseInt(guarantor2ID.substring(0,guarantor2ID.indexOf('.')));
  var pguarantor3ID = parseInt(guarantor3ID.substring(0,guarantor3ID.indexOf('.')));

  //Query to add new loan request information to the loan table
  pool.query(
    'INSERT INTO "loan" ("borrowerID", "loanAmount", "loanDesc", guarantee, "statusMessage", "guarantor1ID", "guarantor2ID", "guarantor3ID","kikobaKey") VALUES (' +
      borrowerID +
      "," +
      parseFloat(loanAmount) +
      ",'" +
      loanDesc +
      "','" +
      guarantee +
      "','Ombi lako la mkopo linajadiliwa kupitishwa.'," +
      pguarantor1ID +
      "," +
      pguarantor2ID +
      "," +
      pguarantor3ID +
      "," +
      kikobaKey +
      ");",
    (error, results) => {
      if (error) {
        throw error;
      }

      //Put guarantors member ID in an array in order to loop through them for adding notification
      var guarantors = [pguarantor1ID, pguarantor2ID, pguarantor3ID];

      guarantors.forEach(function (guarantorID) {
        //Query to retrieve userID, firstName, lastName and kikobaID of the borrower
        pool.query(
          'SELECT "userKey", "kikobaKey", "firstName", "lastName", "kikobaName" FROM "user" JOIN "member" ON "userID" = "userKey" JOIN "kikoba" ON "kikobaKey" = "kikobaID" WHERE "memberID" = ' +
            borrowerID +
            ";",
          (error, borrower) => {
            if (error) {
              throw error;
            }
            var borrowerUserID = borrower.rows[0].userKey;
            var borrowerFirstName = borrower.rows[0].firstName;
            var borrowerLastName = borrower.rows[0].lastName;
            var kikobaID = borrower.rows[0].kikobaKey;
            var kikobaName = borrower.rows[0].kikobaName;

            //Query to retrieve userID of the guarantor selected by the borrower
            pool.query(
              'SELECT "userKey" FROM "member" WHERE "memberID" = ' +
                guarantorID +
                ";",
              (error, guarantor) => {
                if (error) {
                  throw error;
                }
                var guarantorUserID = guarantor.rows[0].userKey;

                //Query to notify user/member of kikoba for being selected as guarantor by the borrower
                pool.query(
                  'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody", notifier, "kikobaInvolved") VALUES (' +
                    guarantorUserID +
                    ",'Udhamini wa mkopo.','" +
                    borrowerFirstName +
                    " " +
                    borrowerLastName +
                    ' amekuchagua kuwa mdhamini wake wa mkopo katika kikoba "' +
                    kikobaName +
                    "\".'," +
                    borrowerUserID +
                    "," +
                    kikobaID +
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
      });

      res.json(true);
    }
  );
}

module.exports = { requestLoan };
