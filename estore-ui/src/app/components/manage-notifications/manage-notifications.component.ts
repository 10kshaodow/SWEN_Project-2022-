import { Component, OnInit } from '@angular/core';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { UserService } from 'src/app/services/user/user.service';
import { Notification } from 'src/app/types/Notification';
import { User } from 'src/app/types/User';

@Component({
  selector: 'app-manage-notifications',
  templateUrl: './manage-notifications.component.html',
  styleUrls: ['./manage-notifications.component.css'],
})
export class ManageNotificationsComponent implements OnInit {
  newNotification: Notification = { id: 0, title: '', message: '' };
  allNotifications: Notification[] = [];
  notificationSelected: Map<Number, boolean> = new Map<Number, boolean>();
  showMakeNew: boolean = false;

  allUsers: User[] = [];
  userSelected: Map<string, boolean> = new Map<string, boolean>();

  constructor(
    private notificationService: NotificationService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.getAllNotifications();
  }

  getAllNotifications(): void {
    this.allNotifications = [];
    this.notificationService
      .getAllNotifications()
      .subscribe((notifications) => {
        this.allNotifications = notifications;
        this.allNotifications.forEach((notification) => {
          if (!this.notificationSelected.has(notification.id)) {
            this.notificationSelected.set(notification.id, false);
          }
        });
      });
  }

  deleteSelected(): void {
    this.allNotifications.forEach((notification: Notification) => {
      if (this.notificationSelected.get(notification.id)) {
        this.notificationService
          .deleteNotification(notification.id)
          .subscribe((retNotification: Notification) => {
            this.getAllNotifications();
            // this.allNotifications.splice(this.allNotifications.findIndex((notification: Notification) => { return retNotification.id === notification.id }), 1);
          });
      }
    });
  }

  deleteById(id: number) {
    this.notificationService
      .deleteNotification(id)
      .subscribe((retNotification: Notification) => {
        this.getAllNotifications();
        // this.allNotifications.splice(this.allNotifications.findIndex((notification: Notification) => { return retNotification.id === notification.id }), 1);
      });
  }

  newNotificationSelected(): void {
    this.newNotification = { id: 0, title: '', message: '' };
    this.getAllUsers();
    this.showMakeNew = true;
  }

  getAllUsers(): void {
    this.allUsers = [];
    this.userSelected = new Map<string, boolean>();
    this.userService.getAllUsers().subscribe((users) => {
      this.allUsers = users;
      this.allUsers.forEach((user) => {
        if (!this.userSelected.has(user.username)) {
          this.userSelected.set(user.username, false);
        }
      });
    });
  }

  setNewNotificationTitle(title: string): void {
    this.newNotification.title = title;
  }

  setNewNotificationMessage(message: string): void {
    this.newNotification.message = message;
  }

  createNotification(): void {
    let selectedUsers: User[] = [];
    this.allUsers.forEach((user) => {
      if (this.userSelected.get(user.username)) {
        selectedUsers.push(user);
        this.userSelected.set(
          user.username,
          !this.userSelected.get(user.username)
        );
      }
    });
    selectedUsers.forEach((user) => {
      console.log('user selected: ' + user.username);
    });

    this.notificationService
      .addNotification(this.newNotification)
      .subscribe((retNotification: Notification) => {
        this.notificationService.addNotificationToUsers(
          retNotification,
          selectedUsers
        );
        this.getAllNotifications();
      });
    this.showMakeNew = false;
  }

  selectUser(user: User) {
    this.userSelected.set(user.username, !this.userSelected.get(user.username));
  }

  isUserSelected(user: User) {
    return this.userSelected.get(user.username) ?? false;
  }

  cancelNotification(): void {
    this.getAllNotifications();
    this.showMakeNew = false;
  }
}
