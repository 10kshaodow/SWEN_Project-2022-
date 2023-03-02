import { Component, OnInit } from '@angular/core';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { UserService } from 'src/app/services/user/user.service';
import { Notification } from 'src/app/types/Notification';
import { User } from 'src/app/types/User';

@Component({
  selector: 'app-all-notifications',
  templateUrl: './all-notifications.component.html',
  styleUrls: ['./all-notifications.component.css'],
})
export class AllNotificationsComponent implements OnInit {
  notifications: Notification[] = [];
  userService: UserService;

  constructor(
    userService: UserService,
    private notificationService: NotificationService
  ) {
    this.userService = userService;
  }

  ngOnInit(): void {
    this.userService.userCurrentlySignedIn.subscribe((signedIn) => {
      if (signedIn) {
        this.setNotificationList();
      }
    });
    this.closeAllNotifications();
  }

  setNotificationList(): void {
    let notificationIds: number[] =
      this.userService.getCurrentUser().notificationHistory;
    this.notifications = [];
    notificationIds.forEach((notifId) => {
      this.notificationService
        .getNotification(notifId)
        .subscribe((notification) => {
          this.notifications.push(notification);
        });
    });
  }

  closeNotification(id: number) {
    if (!this.userService.isSignedIn()) return;

    let user: User = this.userService.getCurrentUser();

    if (user.notifications === null) user.notifications = [];
    if (user.notificationHistory === null) user.notificationHistory = [];

    let index = -1;
    user.notificationHistory.forEach((noti, i) => {
      if (noti == id) index = i;
    });

    if (index == -1) return;

    let newList = user.notificationHistory.filter((noti) => noti !== id);
    user.notificationHistory = newList;

    this.userService.updateUser(user).subscribe((updatedUser) => {
      this.userService.setCurrentUser(updatedUser);
      this.setNotificationList();
    });
  }

  closeAllNotifications(): void {
    if (this.userService.isSignedIn()) {
      let user: User = this.userService.getCurrentUser();
      if (user.notifications === null) user.notifications = [];
      if (user.notificationHistory === null) user.notificationHistory = [];
      user.notifications.forEach((notification) => {
        user.notificationHistory.push(notification);
      });
      user.notifications = [];
      this.userService.updateUser(user).subscribe((updatedUser) => {
        this.userService.setCurrentUser(updatedUser);
        this.setNotificationList();
      });
    }
  }

  clearNotifications(): void {
    console.log('clearing notifications');
    if (this.userService.isSignedIn()) {
      let user: User = this.userService.getCurrentUser();
      user.notifications = [];
      user.notificationHistory = [];
      this.userService.updateUser(user).subscribe((updatedUser) => {
        this.userService.setCurrentUser(updatedUser);
        this.setNotificationList();
      });
    }
  }
}
