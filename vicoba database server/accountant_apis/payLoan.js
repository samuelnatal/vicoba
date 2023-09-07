const { pool } = require("../database");

function payLoan(req, res) {
  var {
    loanID,
    borrowerUserID,
    borrowerMemberID,
    kikobaName,
    date,
    accountantID,
    kikobaID,
    updatedWallet,
    debtAmount
  } = req.body;

  //Query to update the wallet status of the kikoba after paying loan.
  pool.query(
    'UPDATE "kikoba" SET "kikobaWallet"  = ' +
      updatedWallet +
      ' WHERE "kikobaID" = ' +
      kikobaID +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }

      //Query to update the status of the approved loan request to paid
      pool.query(
        'UPDATE "loan" SET status  = \'paid\', "statusMessage" = \' Mkopo wako umelipwa.\' WHERE "loanID" = ' +
          loanID +
          ";",
        (error, results) => {
          if (error) {
            throw error;
          }

          //Query to add loan debt provided to the member.
          pool.query(
            'INSERT INTO "debt" ("memberKey", "debtAmount", "debtDesc", "kikobaKey") VALUES (' +
              borrowerMemberID +
              "," +
              debtAmount +
              ",'Mkopo.'," +
              kikobaID +
              ");",
            (error, results) => {
              if (error) {
                throw error;
              }
            }
          );

          //Query to notify a user of his loan request being rejected.
          pool.query(
            'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody", notifier, "kikobaInvolved") VALUES (' +
              borrowerUserID +
              ",'Taarifa ya mkopo.','Maombi ya mkopo uliyotuma kwenye kikundi \"" +
              kikobaName +
              '" tarehe ' +
              date +
              " yamelipwa.'," +
              accountantID +
              "," +
              kikobaID +
              ");",
            (error, results) => {
              if (error) {
                throw error;
              }
            }
          );
          res.json(true);
        }
      );
    }
  );
}

module.exports = { payLoan };
