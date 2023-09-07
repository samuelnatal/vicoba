const { pool } = require("../database");

function register(req, res) {
  //Destructure kikoba user object received from the retrofit library
  var {
    firstName,
    lastName,
    email,
    gender,
    dob,
    pwd,
    phone,
    address,
    occupation,
  } = req.body;

  //Query to save new user information to the user table
  pool.query(
    'INSERT INTO "user" ("firstName", "lastName", email, gender, dob, pwd, phone, "addressKey", "occupationKey") VALUES (\'' +
      firstName +
      "','" +
      lastName +
      "','" +
      email +
      "','" +
      gender +
      "','" +
      dob +
      "','" +
      pwd +
      "','" +
      phone +
      "','" +
      parseInt(address.substring(0, 1)) +
      "','" +
      parseInt(occupation.substring(0, 1)) +
      "');",
    (error, results) => {
      if (error) {
        throw error;
      } else {
        //Query to return new successful user as an active kikoba user to the vicoba app
        pool.query(
          'SELECT "userID", "firstName", "lastName", email, gender,to_char(dob, \'YYYY-MM-DD\') AS date, phone, "addressKey", region, district, ward, occupation_name FROM "address" JOIN "user" ON "addressID" = "addressKey"JOIN "occupation" ON "occupationKey" = "occupationID" WHERE email = \'' +
            email +
            '\'ORDER BY "userID" DESC LIMIT 1;',
          (error, results) => {
            if (error) {
              throw error;
            }

            //Query to save new notification about successful creating user account
            pool.query(
              'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody") VALUES (' +
                results.rows[0].userID +
                ",'Account.','Welcome " +
                results.rows[0].firstName +
                ", to the Vicoba app your account has been created successful.');",
              (error, results) => {
                if (error) {
                  throw error;
                }
              }
            );
            res.json(results.rows[0]);
          }
        );
      }
    }
  );
}
module.exports = { register };
