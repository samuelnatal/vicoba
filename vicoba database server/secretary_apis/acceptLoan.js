const { pool } = require("../database");

function acceptLoan(req, res) {
  var { loanID, borrowerUserID, kikobaName, date, secretaryID, kikobaID } = req.body;

  //Query to update the status of the pending request to rejected
  pool.query(
    'UPDATE "loan" SET status  = \'accepted\', "statusMessage" = \' Mkopo wako umekubaliwa na kamati ya mikopo.\' WHERE "loanID" = ' +
      loanID +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }

      //Query to notify a user of his loan request being rejected.
      pool.query(
        'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody", notifier, "kikobaInvolved") VALUES (' +
          borrowerUserID +
          ",'Taarifa ya mkopo.','Maombi ya mkopo uliyotuma kwenye kikundi \"" +
          kikobaName +
          '" tarehe ' +
          date +
          " yamekubaliwa.'," +
          secretaryID +
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

module.exports = { acceptLoan };
