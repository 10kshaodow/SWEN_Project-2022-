import { Component, OnInit } from '@angular/core';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { UserService } from 'src/app/services/user/user.service';
import { User } from 'src/app/types/User';
import { Notification } from 'src/app/types/Notification';

@Component({
  selector: 'app-view-notification',
  templateUrl: './view-notification.component.html',
  styleUrls: ['./view-notification.component.css'],
})
export class ViewNotificationComponent implements OnInit {
  notifications: Notification[] = [];
  userService: UserService;
  // currentUser?: User = undefined;

  constructor(
    userService: UserService,
    private notificationService: NotificationService
  ) {
    this.userService = userService;
  }

  serverUpdate(): void {
    console.log('setting notifications');
    if (this.userService.isSignedIn()) {
      this.userService
        .getUser(this.userService.getCurrentUser().username)
        .subscribe((user) => {
          this.userService.setCurrentUser(user);
        });
    }
  }

  setNotificationList(): void {
    if (this.userService.isSignedIn()) {
      this.notifications = [];
      if (this.userService.getCurrentUser().notifications === null) {
        this.userService.getCurrentUser().notifications = [];
      }
      this.userService
        .getCurrentUser()
        .notifications.forEach((notificationId) => {
          this.notificationService
            .getNotification(notificationId)
            .subscribe((notification: Notification) => {
              if (notification !== null) {
                // if the id doesnt exist yet then add it
                if (
                  this.notifications.findIndex(
                    (notif) => notification.id === notif.id
                  ) == -1
                ) {
                  this.notifications.push(notification);
                }
              }
            });
        });
    } else {
      this.notifications = [];
    }
  }

  ngOnInit(): void {
    this.notificationService.notificationUpdates.subscribe(
      (message: string) => {
        this.serverUpdate();
      }
    );
    this.userService.userCurrentlySignedIn.subscribe((signedIn: boolean) => {
      this.setNotificationList();
    });
    this.setNotificationList();
  }

  test() {
    console.log('test');
  }

  onNotificationRecieved(): void {
    console.log('notification recieved');
    if (this.userService.isSignedIn()) {
      this.userService
        .getUser(this.userService.getCurrentUser().username)
        .subscribe((user) => {
          this.userService.setCurrentUser(user);
          this.setNotificationList();
        });
    }
  }

  onCloseNotification(id: number): void {
    if (this.userService.isSignedIn()) {
      let index = this.userService
        .getCurrentUser()
        .notifications.findIndex((notificationId: number) => {
          return notificationId === id;
        });
      console.log('notifications: ' + this.notifications);
      console.log('index: ' + index);
      if (index != undefined && index !== -1) {
        let removedId = this.userService
          .getCurrentUser()
          .notifications.splice(index, 1)[0];
        if (this.userService.getCurrentUser().notificationHistory === null)
          this.userService.getCurrentUser().notificationHistory = [];
        this.userService.getCurrentUser().notificationHistory.push(removedId!);
      }
      this.userService
        .updateUser(this.userService.getCurrentUser())
        .subscribe(() => {
          this.notifications.splice(
            this.notifications.findIndex((notif: Notification) => {
              return id == notif.id;
            }),
            1
          );
          this.setNotificationList();
        });
    }
  }
}
